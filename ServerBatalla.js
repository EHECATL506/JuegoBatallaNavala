/**
	 * @version 1.5
	 * @file Este archivo es un servidor hecho en javascript que sirve como comunicacion entre varias aplicaciones clientes.
     * @author Ehecatl Said Cortina Gamboa
     */

	var io = require("socket.io")(8000);
	var mysql = require ("mysql");

	var usuariosjuego = new Array();

		var connection = mysql.createConnection({
			host :"localhost",
			user: "contramaestre",
			password: "guerramundial",
			database: "batallanaval",
			port: 3306,
		});

		var tablero = new Array(10);
     	for (var fila = 0; fila<10; fila++) {
     	tablero[fila] = new Array(10);
     	}
     	for(var fila = 0; fila<10; fila++){
     	for(var columna = 0; columna<10; columna++){
     		tablero[fila][columna] = 0;
     		}
     	}


	console.log("Servidor en marcha, listo para recibir clientes");
	 io.on("connection", function(socket){
     	console.log("Usuario conectado, Bienvenido a batalla naval");

	 /**
 	* @throws {SQLException} Se envia la excepción cuando la conexión a la base de datos no esta disponible
 	*/	
 	function autenticar(usuario, connection){
 		var query = connection.query("SELECT Usuario, Contraseña FROM usuarios where Usuario=?",[usuario.user], function(error, result){
 			if(error){
 				throw error;
 			} else{
 				var resultado = result;
                    console.log(resultado);
                   // sha2(Contraseña, 256);
 				if(resultado.length > 0){
 					if(resultado[0].Usuario == usuario.user && resultado[0].Contraseña == usuario.pass){
 						io.sockets.in(usuario.user).emit("verificado", true);
		  				} else{
		  					io.sockets.in(usuario.user).emit("verificado", false);
		  				}
		  			}
		  		}
		  	});

 	}

 	/**
 	* @name Solicitud de juego
 	* @event Asigna el socket a un usuario utilizando salas para poder identificarlos
 	*/
 	socket.on('join', function (usuario) {
 		socket.join(usuario.user); 
 		for(var jugadores = 0; jugadores<usuariosjuego.length; jugadores++){
 			if(usuariosjuego[jugadores] === usuario.user){
 				

 			} else{
 				usuario.push(usuario.user);
 			}
 		}
	    		});


 	/**
 	* @event Evento que recibe el usuario y la contraseña para inciar sesión
 	*/
 	socket.on("entrar", function(usuario){
 		autenticar(usuario, connection);

 	});
 	
 	function registrarNuevoUsuario(connection, nuevoUsuario){
 		var query1 = connection.query("SELECT * FROM usuarios where usuario=?", [nuevoUsuario.user], function(error, result){
 			if(error){
 				console.log(error);
 			} else{
 				var resultado = result;
 				if(resultado.length > 0){
 					io.sockets.in(nuevoUsuario.user).emit('registroCorrecto', false);
 				} else{
 					var query = connection.query("insert into usuarios values(?, ?, ?, ?)",[nuevoUsuario.user, nuevoUsuario.pass, nuevoUsuario.correo, nuevoUsuario.nombre], function(error, result){
 						if(error){
 							console.log(error);
 						} else{
 							io.sockets.in(nuevoUsuario.user).emit('registroCorrecto', true);
 						}
 					});

 				}

 			}
 		});
 	}

 	/**
 	* @event Evento que registra los usuarios llama a la funcion registrarNuevoUsuario
 	*/
 	socket.on("registrar", function(nuevoUsuario){
 		registrarNuevoUsuario(connection, nuevoUsuario);
 	});


 	function crearEjeX(ubicacion){
 		
 		return parseInt(ubicacion.charAt(0));
 	}
 	function crearEjeY(ubicacion){

 		return parseInt(ubicacion.charAt(2));
 	}
 	socket.on("acomodarBarcosEnTablero", function(ubicacion){

		console.log(ubicacion);
 		tablero[crearEjeX(ubicacion.barco1)][crearEjeY(ubicacion.barco1)] = 1;
 		tablero[crearEjeX(ubicacion.barco2)][crearEjeY(ubicacion.barco2)] = 1;
 		tablero[crearEjeX(ubicacion.guerra1)][crearEjeY(ubicacion.guerra1)] = 1;
 		tablero[crearEjeX(ubicacion.guerra2)][crearEjeY(ubicacion.guerra2)] = 1;
 		tablero[crearEjeX(ubicacion.guerra3)][crearEjeY(ubicacion.guerra3)] = 1;
 		tablero[crearEjeX(ubicacion.guerra4)][crearEjeY(ubicacion.guerra4)] = 1;
 		tablero[crearEjeX(ubicacion.guerra5)][crearEjeY(ubicacion.guerra5)] = 1;
 		tablero[crearEjeX(ubicacion.submarino1)][crearEjeY(ubicacion.submarino1)] = 1;
 		tablero[crearEjeX(ubicacion.submarino2)][crearEjeY(ubicacion.submarino2)] = 1;
 		tablero[crearEjeX(ubicacion.submarino3)][crearEjeY(ubicacion.submarino3)] = 1;
 		tablero[crearEjeX(ubicacion.carguero1)][crearEjeY(ubicacion.carguero1)] = 1;
 		tablero[crearEjeX(ubicacion.carguero2)][crearEjeY(ubicacion.carguero2)] = 1;
 		tablero[crearEjeX(ubicacion.carguero3)][crearEjeY(ubicacion.carguero3)] = 1;
 		tablero[crearEjeX(ubicacion.carguero4)][crearEjeY(ubicacion.carguero4)] = 1;

 	});

 	/**
 	* @event Se envia la solicitud al cliente que muestra una solicitud de batalla, mientras que detiene otros eventos
 	*/
 	socket.on("solicitudBatallaSaliente", function(solicitud){
 		io.sockets.in(solicitud.contrincante).emit('solicitudBatallaEntrante', solicitud);
 	});
 	socket.on("acept", function(ac){
 		io.sockets.in(ac.retador).emit("comenzarBatalla", ac);
 	});


 	var counter = 14;
 	
 	/**
 	* @event registra el disparo y crea toda la accion del juego.
 	*/
 	socket.on("tiro1", function(tiro){
 		var coo = tiro.coordenada;
 		
 		var ejex1 = parseInt(coo.charAt(0));
 		var ejey1 = parseInt(coo.charAt(1));

 		if(tablero[ejex1][ejey1] === 1){
 			if(counter === 1){
 				io.sockets.in(tiro.retador).emit('ganaste', true);
 				io.sockets.in(tiro.contrincante).emit('ganaste', false);
 			} else{
 				console.log("Le diste a un barco");
 				io.sockets.in(tiro.retador).emit('acertar', true);
 				io.sockets.in(tiro.contrincante).emit('marcar', tiro);
 				counter--;
 			}
 		} else{
 			console.log("le diste al agua");
 			io.sockets.in(tiro.retador).emit('acertar', false);
 			io.sockets.in(tiro.contrincante).emit('cambio', true);
 			io.sockets.in(tiro.contrincante).emit('marcar', tiro);
 		}
 	});
 });