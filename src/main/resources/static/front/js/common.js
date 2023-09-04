
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

/** 메인 페이지 리스트 출력 */
window.addEventListener("DOMContentLoaded", function() {
    const itemsPerPage = 6;
    const items = document.querySelectorAll('.main_prd_li');
    const prevButton = document.getElementById('prevPage');
    const nextButton = document.getElementById('nextPage');
    const noResultsMessage = document.querySelector('.no-results');
    let currentPage = 1;

    function showPage(page) {
        for (let i = 0; i < items.length; i++) {
            items[i].style.display = 'none';
        }
        for (let i = (page - 1) * itemsPerPage; i < page * itemsPerPage; i++) {
            if (items[i]) {
                items[i].style.display = 'block';
            }
        }
    }

    function updatePaginationButtons() {
        if (currentPage === 1) {
            prevButton.style.display = 'none';
        } else {
            prevButton.style.display = 'block';
        }
        if (currentPage * itemsPerPage >= items.length) {
            nextButton.style.display = 'none';
        } else {
            nextButton.style.display = 'block';
        }
    }

    function checkResults() {
        if (items.length === 0) {
            noResultsMessage.style.display = 'block';
        } else {
            noResultsMessage.style.display = 'none';
        }
    }

    prevButton.addEventListener('click', () => {
        if (currentPage > 1) {
            currentPage--;
            showPage(currentPage);
            updatePaginationButtons();
        }
    });

    nextButton.addEventListener('click', () => {
        if (currentPage * itemsPerPage < items.length) {
            currentPage++;
            showPage(currentPage);
            updatePaginationButtons();
        }
    });

    showPage(currentPage);
    updatePaginationButtons();
    checkResults();
});


