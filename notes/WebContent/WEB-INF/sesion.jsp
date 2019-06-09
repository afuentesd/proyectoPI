<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Tus Notas | Sesión</title>
		<meta name="description" content="PÃ¡gina principal de la web de intercambio de idiomas">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">		
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

		
	
		
	</head>
	<body>
		<div class="btm_border">
			<div class="h_bg">
				<div class="wrap">
					<div class="header">
						<div class="logo">
							<a><img src="img/banner.jpg" alt=""></a>
						</div>
						
						<div class="clear"> </div>
					</div>
					<div class='h_btm'>
						<div class='cssmenu'>
							<ul>
								<li class='active'>
									<a href='SessionServlet'><span>Iniciar Sesión</span></a>
								</li>							
								<li class='last'>
									<a href='registro.html'><span>Registrarse</span></a>
								</li>
								
							</ul>
						</div>
						
						<div class="clear"> </div>
					</div>
				</div>
			</div>
		</div>
		<!--main-->
		<div class="main_btm">
			<div class="wrap">
				<div class="main">
					<div class="contact">
						<div class="section group">
							
							<div class="col span_2_of_4">
								<div class="contact-form">
									<h2 class="style">Iniciar Sesión</h2>
									<form method="post" action="?" name="myForm"  >
									<c:forEach var="messages" items="${messages}">
									<span><label>${messages}</label></span>
									</c:forEach>
										<div>
											<span><label>USUARIO(*)</label></span>
											<span>
												<input id="usuario" name="usuario" type="text" class="textbox" placeholder="Usuario">
											</span>
										</div>
										<div>
											<span><label>CONTRASEÑA(*)</label></span>
											<span>
												<input id="contrasena" name="contrasena" type="password" class="textbox" placeholder="Contraseña">
											</span>
										</div>
										<div>
										</div>
										<div>
											<span>
												<input type="submit" value="Iniciar Sesión">
											</span>
										</div>
									</form>
								</div>
							</div>
							<div class="clear"> </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--footer-->
		<div class="footer-bg">
			<div class="wrap">
				<div class="footer">
										
					<div class="box1">
						<h4 class="btm">Contacta con nosotros</h4>
						<div class="box1_address">
							<p>
								Escuela Politecnica
							</p>
							<p>
								10001 Caceres
							</p>
							<p>
								España
							</p>
							<p>
								Telefono:  927 25 71 95
							</p>
							<p>
								Fax: 927 257 203
							</p>
							<p>
								Email: <span>adminnotes@gmail.es</span>
							</p>
						</div>
					</div>
					<div class="clear"> </div>
				</div>
			</div>
		</div>
		<!--footer1-->
		<div class="ftr-bg">
			<div class="wrap">
				<div class="footer">
					<div class="copy">
						© Copyright by Miguel Ros Rincón
						<img src="img/footer.png" alt="" />
					</div>
					<div class="clear"> </div>
				</div>
			</div>
		</div>
	</body>
</html>