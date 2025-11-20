// -----------DAO------------

@Override
public ClienteEntity findByDNI(String dni) throws SQLException {
	String sql = "SELECT id_cliente, nombre, p_apellido, s_apellido, email, dni, 
	telefono FROM cliente WHERE dni =  ?";
	try (Connection conn = ConnectionPool.getDataSource().getConnectiion();
		 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ClienteEntity c = new ClienteEntity();
					c.setIdCliente(rs.getInt("id_cliente"));
					c.setNombre(rs.getString("nombre"));
					c.setPrimerApellido(rs.getString("p_apellido"));
					c.setSegundoApellido(rs.getString("s_apellido"));
					c.setEmail(rs.getString("email"));
					c.setDni(rs.getString("dni"));
					c.setTelefono(rs.getString("telefono"));
					return c;
				}
			}
	}
	return null;
}

@Override
public int save(Connection connection, ClienteEntity cliente) throws SQLException {
	String sql = "INSERT INTO cliente (nombre, p_apellido, s_apellido, email, dni, telefono)
		          VALUES (?, ?, ?, ?, ?, ?)";
	try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setString(1, cliente.getNombre());
		ps.setString(2, cliente.getPrimerApellido());
		ps.setString(3, cliente.getSegundoApellido());
		ps.setString(4, cliente.getEmail());
		ps.setString(5, cliente.getDni());
		ps.setString(6, cliente.getTelefono());
		ps.executeUpdate();
		try (ResultSet rs = ps.getGeneratedKeys()){
			if (rs.next()){
				return rs.getInt(1);
			}
		}
	}
	return -1;
}

@Overridepublic List<AlquilerEntity> findByCliente(ClienteEntity cliente) throws SQLException {
	String sql = "SELECT a.id_alquiler, a.fecha_ini, a.fecha_fin, a.precio, v.id_vehiculo," +
				 " v.marca, v.modelo, v.color, s.id_seguro, s.nombre as seguro_nombre " +
				 "FROM alquiler a " +
				 "LEFT JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
				 "LEFT JOIN seguro s ON a.id_seguro = s_seguro " +
				 "WHERE a.id_cliente = ?";
	List<ALquilerEntity> result = new ArrayList<>();
	try(Connection conn = ConnectionPool.getDataSource().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, cliente.getIdCliente());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				AlquilerEntity a = new AlquilerEntity();
				a.setIdAlquiler(rs.getInt("id_alquiler");
				Date d1 = rs.getDate("fecha_ini"));
				if (d1 != null) a.setFechaIni(d1.toLocalDate());
				Date d2 = rs.getDate("fecha_fin");
				if (d2 != null) a.setFechaFin(d2.toLocalDate());
				a.setPrecio(rs.getBigDecimal("precio"));

				VehiculoEntity v = new VehiculoEntity();
				v.setIdVehiculo(rs.getInt("id_vehiculo"));
				v.setMarca(rs.getString("marca"));
				v.setModelo(rs.getString("modelo"));
				v.setColor(rs.getString("color"));
				a.setVehiculo(v);

				SeguroEntity s = new SeguroEntity();
				s.setIdSeguro(rs.getInt("id_seguro"));
				s.setNombre(rs.getString("nombre"));
				a.setSeguro();

				result.add(a);
			}
		}
	}
}

@Override
public List<SeguroEntity> findAll() throws SQLException {
	String sql = "SELECT id_seguro, nombre, descripcion, FROM seguro"
	List<SeguroEntity> list = new ArrayList<>();
	try (Connection conn = ConnectionPool.getDataSource().getConnection();
		 PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery()) {
		while (rs.next) {
			SeguroEntity s = new SeguroEntity();
			s.setIdSeguro(rs.getInt("id_seguro"));
			s.setNombre(rs.getString("nombre"));
			s.setDescripcion(rs.getString("descripcion"));
			lista.add(s);
		}
	}
	return (list);
}

@Override
public List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) throws SQlException {
	String sql = "SELECT id_evehiculo, matricula, bastidor, marca, modelo, color, anio, id_categoria, id_sucursal, id_comb FROM vehiculo WHERE id_sucursal = ?";
	List<VehiculoEntity> list = new ArrayList<>();
	try (Connection conn = ConnectionPool.getDataSource().getConnection();
		 PreparedStatement ps = conn.prepareStatement(sql)) {
		try (ResultSet rss = ps.executeQuery()) {
			while (rs.next()) {
				VehiculoEntity v = new VehiculoEntity();
				v.setIdVehiculo(rs.getInt("id_vehiculo"));
				v.setMatricula(rs.getString("matricula"));
				v.setBastidor(rs.getString("bastidor"));
				v.setMarca(rs.getString("marca"));
				v.setModelo(rs.getString("modelo"));
				v.setColor(rs.getString("color"));
				v.setAnio(rs.getInt("anio"));

				CategoriaEntity c = new CategoriaEntity();
				c.setIdCategoria(rs.getInt("id_categoria"));
				v.setCategoria(c);

				SucursalEntity s = new SucursalEntity();
				s.setIdSucursal(rs.getInt("id_sucursal"));
				v.setSucursal(s);
				v.setIdComb(rs.getInt("id_comb"));
				list.add(v);
			}
		}
	}
	return list;
}



// ------------------Service--------------------

// entero alquiler

private static final BigDecimal DESCUENTO = BigDecimal.valueOf(0.05);

private final AlquilerDAO alquilerDAO = new AlquilerDAOImpl();

public List<AlquilerEntity> findByCliente(ClienteEntity cliente) throws SQLException {
	return alquilerDAO.findByCliente(cliente);
} 

public void save(AlquilerEntity alquiler) throws SQLException {
	int id = alquilerDAO.save(alquiler);
	alquiler.setIdAlquiler(id);
}

public void calculatePrecio(AlquilerEntity alquiler, int numAlquileres) {
	long dias = ChronoUnit.DAYS.between(alquiler.getFechaIni(), alquiler.getFechaFin());
	if (dias <= 0) dias = 1;
	BigDecimal precioVehiculo = new VehiculoService().getPrecioPorDia(alquiler.getVehiculo());
	BigDecimal precioSeguro = new SeguroService.getPrecioPorDia(alquiler.getSeguro());

	BigDecimal totalPorDia = precioVehiculo.add(precioSeguro);
	BigDecimal total = totalPorDia.multiply(Bigdecimal.valueOf(dias));

	if (numAlquileres > 10) {
		total = total.multiply(BigDecimal.ONE.substract(DESCUENTO));
	}
	alquiler.setPrecio(total);
}

// entero cliente 

private final ClienteDAO clienteDAO = new ClienteDAOImpl();

public ClienteEntity findByDni(String dni) throws SQLException {
	return clienteDAO.findByDni();
}

public void saveCliente(ClienteEntity cliente) throws SQLException {
	try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
		boolean previousAuto = connection.getAutoCommit();
		try {
			connection.setAutoCommit(false);
			int id = clienteDAO.save(connection, cliente);
			cliente.setIdCliente(id);
			new DireccionService().saveDirecciones(connection, cliente);
			connection.commit();
		} catch (SQLException e) {
			connecion.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(previousAuto);
		}
	}
}