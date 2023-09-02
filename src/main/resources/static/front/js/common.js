document.querySelector('.dropbtn').addEventListener('click', function() {
    var dropdownContent = document.querySelector('.header_drop_menu');
    dropdownContent.style.display = (dropdownContent.style.display === 'block') ? 'none' : 'block';
});