var catalogs = [];

$(document).ready(function() {
	
	$('.mainLogo, .altLogo').click(function() {
		$(location).attr('href','index.html');
	});
	
	$('.beClient').click(function (){
		$(location).attr('href','RegistrationClient.html');
	});
	
	$('.beRepr').click(function (){
		$(location).attr('href','Registration.html');
	});
	
	$('.beCoord').click(function (){
		$(location).attr('href','RegistrationCoord.html');
	});

	
	// Кнопка с меню
	function checkWidth(width){
		if ($(window).width() <= 577){
			$('.largeMenu').hide();
			$('.smallMenu').show();
			
			$('.firstBut').hide();
			$('.secondBut').hide();
			
			$('.mainLogo').hide();
			$('.altLogo').show();
			
			$('.agreement').hide();
			$('.agreementAlt').show();
		}
		else{
			$('.largeMenu').show();
			$('.smallMenu').hide();
			
			$('.firstBut').show();
			$('.secondBut').show();
			
			$('.mainLogo').show();
			$('.altLogo').hide();
			
			$('.agreement').show();
			$('.agreementAlt').hide();
		}
	}
	
	// Картинки для каталога
	function getCatalogs(){
		$.getJSON('http://catalogs.promoavon.ru/periods/sroki2018/', function(data) {
			var items = [];
			for (key in data){
				console.log(key + " - " + data[key]);
			}
		});
	}
	
	// Функция определения текущего каталога
	function currentCatalog(){
		var current_date = new Date();
		
		var init_day = new Date(2018, 6, 30, 0, 0, 0, 0);
		
		// Генерируем даты каталогов на 5 лет
		for (var i = 0; i < 86; i++){
			catalogs[i] = init_day.getTime();
			init_day.setDate(init_day.getDate() + 21);
		}
		
		var i = 1;
		while(true){
			if (current_date.getTime() > catalogs[i-1] && current_date.getTime() < catalogs[i]){

				$('#textCat1').html('<b>Avon ' + (11 + i) + ' ' + new Date(catalogs[i-1]).getFullYear() +'</b>');
				$('#textCat2').html('<b>Avon ' + (11 + i - 1) + ' ' + new Date(catalogs[i-1]).getFullYear() +'</b>');
				$('#imgCat1').attr('src', 'resources/catalogs/' + (11 + i) + '.jpg');
				$('#imgCat2').attr('src', 'resources/catalogs/' + (11 + i - 1) + '.jpg');
				
				
				var day = new Date(catalogs[i-1]).getDate() <= 9 ? ('0' + new Date(catalogs[i-1]).getDate()) : new Date(catalogs[i-1]).getDate(); 
				var month = new Date(catalogs[i-1]).getMonth()+1 <= 9 ? ('0' + (new Date(catalogs[i-1]).getMonth()+1)) : new Date(catalogs[i-1]).getMonth()+1;
				
				var next_day = new Date(catalogs[i]).getDate()-1 <= 9 ? ((new Date(catalogs[i]).getDate()-1 == 0 ? 31 : '0' + new Date(catalogs[i]).getDate()-1)) : '0' + new Date(catalogs[i]).getDate()-1;				
				var next_month = new Date(catalogs[i]).getMonth()+1 <= 9 ? ('0' + (new Date(catalogs[i]).getMonth()+1)) : new Date(catalogs[i]).getMonth()+1;
				
				$('#catalogDate').html((11+i-1) + '. ' + new Date(catalogs[i]).getFullYear() + ' (с ' + day + '.' + month + ' по ' + next_day + '.' + next_month + ')');
				return;
			}
			else
				
			i++;
		}
	}
	
	$(function (){
		getCatalogs();
		currentCatalog();
		checkWidth($(window).width());
	});
	
	$(window).resize(function(){
		checkWidth($(window).width());
	});
	
	// Переход на страницу с каталогами
	$('#CatalogsBut').click(function(){
		$(location).attr('href','Catalogs.html');
	});
	
	// Копирование адреса
	$('#checkAdress').click(function (){
		if ($('#checkAdress').prop('checked') == true)
			$('#propAdress').val($('#factAdress').val());
		else
			$('#propAdress').val('');
	});
	
});