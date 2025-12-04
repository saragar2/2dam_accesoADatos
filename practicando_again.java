//----------HIBERNATE-----------------

//AlquilerEntity

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "alquiler")
public class AlquilerEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alquiler")
	private int id;

	@Column(name = "fehca_ini")
	private LocalDate fechaIni;

	@Column(name = "fecha_fin")
	private LocalDate fechaFin;

	@Column(name = "precio")
	private BigDecimal precio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private ClienteEntity cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vehiculo")
	private VehiculoEntity vehiculo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seguro")
	private SeguroEntity seguro;
}

//ClienteEntity

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {

	@Serial
	private static final long serialVersionUID =  1L;

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY);
	@Column(name = "id_cliente")
	private int id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "p_apellido")
	private String primerApellido;

	@Column(name = "s_apellido")
	private String segundoApellido;

	@Column(name = "email", unique = true)
	private String email;
}

//ClienteService

public class ClienteService {
	private final ClienteDAO clienteDAO = new ClienteDaoImpl();

	public ClienteEntity findByDNI(String dni) {
		return cleitneDAO.findByDNI();
	}

	public void saveCliente(ClienteEntity cliente) {
		clienteDAO.save(cliente);
	}
}

//ClienteDAOImpl

public class ClienteDAOImpl implements clienteDAO {

	@Override
	public ClienteEntity findByDNI(String dni) {
		try (sSession session = HibernateConfiguration.getSessionFactory().openSession()) {
			String hql = "from ClienteEntity where dni = :dni";
			Query<ClienteEntity> query = session.createQuery(hql, ClienteEntity.class)
					.setParameter("dni", dni)
					.setReadOnly(true);
			ClienteEntity cliente = query.uniqueResult();
			Hibernate.initialize(cliente.getAlquileres());
			for (AlquilerEntity alquilerEntity : cliente.getAlquileres()) {
				Hibernate.initialize(alquilerEntity.getVehiculo());
			}
			return cliente;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void save(ClienteEntity cliente) {
		Transaction transaction = null;
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(cliente);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		}
	}

	@Override
	public List<ClienteEntity> findByNombreContains(String texto) {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			String hql = "FROM ClienteEntity c " +
						"WHERE LOWER(c.nombre) LIKE LOWER(:texto)";
			Query<ClienteEntity> query = session.createQuery(hql, ClienteEntity.class)
					.setParameter("texto", "%" + texto + "%")
					.setReadOnly(true);
			List<ClienteEntity> clientes = query.list();
			for (ClienteEntity c : clientes) {
				Hibernate.initialize(c.getDirecciones());
				if (c.getFechaNacimiento() != null) {
					c.setEdad(calcularEdad(c.getFechaNacimiento()));
				}
			}
			return clientes;

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	private int calcularEdad(LocalDate fechaNacimiento) {
		return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}

	@Override
	public boolean deleteByDNI(String dni) {

		Transaction transaction = null;
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			// Buscar cliente por DNI
			String hql = "FROM ClienteEntity WHERE dni = :dni";
			ClienteEntity cliente = session.createQuery(hql, ClienteEntity.class)
					.setParameter("dni", dni)
					.uniqueResult();
			if (cliente == null) {
				return false; // No existe
			}
			// Inicializar alquileres para comprobarlos
			Hibernate.initialize(cliente.getAlquileres());
			// Si tiene alquileres, no se puede eliminar
			if (cliente.getAlquileres() != null && !cliente.getAlquileres().isEmpty()) {
				return false;
			}
			transaction = session.beginTransaction();
			// Hibernate borrará direcciones automáticamente
			session.remove(cliente);
			transaction.commit();
			return true;

		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
	}

}