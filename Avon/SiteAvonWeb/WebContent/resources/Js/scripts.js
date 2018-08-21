var catalogs = [];

jQuery(document).ready(function() {
	
	$('.mainLogo, .altLogo').click(function() {
		$(location).attr('href','index.html');
	});
	
	$('#2button').click(function() {
		$(location).attr('href','Catalog.html');
	});
	
	$('#3button').click(function() {
		$(location).attr('href','Catalog.html');
	});
	
	$('#4button').click(function() {
		$(location).attr('href','Catalog.html');
	});
	
	$('#5button').click(function() {
		$(location).attr('href','Shares.html');
	});
	
	$('.goToForm').click(function() {
		$(location).attr('href','https://myoffice.avon.ru/SPF.html?beautybusiness');
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
		}
		else{
			$('.largeMenu').show();
			$('.smallMenu').hide();
			
			$('.firstBut').show();
			$('.secondBut').show();
			
			$('.mainLogo').show();
			$('.altLogo').hide();
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
//			console.log(init_day);
			init_day.setDate(init_day.getDate() + 21);
		}
		
		
//		console.log("Текущий день: " + current_date + " \n              " + new Date(catalogs[0]) + " \n              " + new Date(catalogs[1]));
//		console.log(catalogs[0] + " " + catalogs[1] + " " + catalogs[2]);
		
//		console.log("Compare: " + (catalogs[1].valueOf() < catalogs[2].valueOf()));
		var i = 1;
		while(true){
			if (current_date.getTime() > catalogs[i-1] && current_date.getTime() < catalogs[i]){
//				console.log("True")
				$('#textCat1').html('<b>Avon ' + (11 + i) + ' ' + new Date(catalogs[i-1]).getFullYear() +'</b>');
				$('#textCat2').html('<b>Avon ' + (11 + i - 1) + ' ' + new Date(catalogs[i-1]).getFullYear() +'</b>');
				$('#imgCat1').attr('src', 'resources/catalogs/' + (11 + i) + '.jpg');
				$('#imgCat2').attr('src', 'resources/catalogs/' + (11 + i - 1) + '.jpg');
				
				
				var day = new Date(catalogs[i-1]).getDate() <= 9 ? ('0' + new Date(catalogs[i-1]).getDate()) : new Date(catalogs[i-1]).getDate(); 
				var month = new Date(catalogs[i-1]).getMonth()+1 <= 9 ? ('0' + (new Date(catalogs[i-1]).getMonth()+1)) : new Date(catalogs[i-1]).getMonth()+1;
				
				var next_day = new Date(catalogs[i]).getDate()-1 <= 9 ? ('0' + new Date(catalogs[i]).getDate())-1 : new Date(catalogs[i]).getDate()-1; 
				var next_month = new Date(catalogs[i]).getMonth()+1 <= 9 ? ('0' + (new Date(catalogs[i]).getMonth()+1)) : new Date(catalogs[i]).getMonth()+1;
				
				$('#catalogDate').html((11+i-1) + '. ' + new Date(catalogs[i]).getFullYear() + ' (с ' + day + '.' + month + ' по ' + next_day + '.' + next_month + ')');
				return;
			}
			else
//				console.log("False")
				
			i++;
		}
//		while(true){
//			
//			if (current_date.getTime() < catalogs[i].getTime() && current_date.getTime() > catalogs[i-1].getTime()){
//				$('.catalog p').html('<b>Avon ' + (11 + i + 1)  + '</b><');
//				return;
//			}
//			i++;
//		}
	}
	
	$(function (){
//		getCatalogs();
		currentCatalog();
		checkWidth($(window).width());
	});
	
	$(window).resize(function(){
		checkWidth($(window).width());
	});
	
	// Скорлл по нажатию кнопки Каталоги AVON
	$('#CatalogsBut').click(function(){
		$(location).attr('href','Catalogs.html');
//		window.scrollTo(0, document.getElementById("Catalogs").scrollHeight + 1295);
	});
	
});