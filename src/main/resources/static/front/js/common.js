
/*
document.querySelector('.dropbtn').addEventListener('click', function() {
    var dropdownContent = document.querySelector('.header_drop_menu');
    dropdownContent.style.display = (dropdownContent.style.display === 'block') ? 'none' : 'block';
});
*/
window.addEventListener("DOMContentLoaded", function() {
    const dropBtn = document.querySelector(".dropbtn");
    dropBtn.addEventListener('click', function() {
        const dropdownContent = document.querySelector('.header_drop_menu');
        dropdownContent.style.display = (dropdownContent.style.display === 'block') ? 'none' : 'block';
    });
});