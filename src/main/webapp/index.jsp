<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<h1>Welcome to DengShaobing!</h1>

	<a href="daddy">Daddy Zone</a>
	<br />
	<a href="">Mummy Zone</a>
	<br />
	<a href="">Baby Zone</a>


	<p>
		And now is
		<script type="text/javascript">
			var now = new Date();
			document.write(1900 + now.getYear() + "-" + (now.getMonth() + 1)
					+ "-" + now.getDate() + " " + now.getHours() + ":"
					+ now.getMinutes() + ":" + now.getSeconds());
		</script>
		. <br /> Shaobing is
		<script type="text/javascript">
			var now = new Date();
			var birth = new Date("2016/04/07");
			document.write(Math.floor((now - birth) / 86400000))
		</script>
		days old. <br /> Wish you happy today :P
	</p>


	<p>
		<em>Thank you for using DengShaobing!</em>
	</p>





	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery-3.1.0.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>

	<script>
		$(function() {
			var nua = navigator.userAgent
			var isAndroid = (nua.indexOf('Mozilla/5.0') > -1
					&& nua.indexOf('Android ') > -1
					&& nua.indexOf('AppleWebKit') > -1 && nua.indexOf('Chrome') === -1)
			if (isAndroid) {
				$('select.form-control').removeClass('form-control').css(
						'width', '100%')
			}
		}) 
	</script>
</body>
</html>