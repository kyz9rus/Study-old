$(document).ready(function() {	
	
	$('#sendBut').click(function(e) {
		if ($('#name2').val().length == 0){
			e.preventDefault();
			alert("Укажите пожалуйста ваше имя");
			return;
		}
		
		if ($('#email').val().length == 0){
			e.preventDefault();
			alert("Укажите пожалуйста ваш email");
			return;
		}
		
		if ($('#message').val().length == 0){
			e.preventDefault();
			alert("Введите пожалуйста текст сообщения");
			return;
		}
		
		if (!$('#checkbox').prop('checked')){
			e.preventDefault();
			alert("Установите флажок о согласии на обработку данных");
			return;
		}
		
	});
	
});