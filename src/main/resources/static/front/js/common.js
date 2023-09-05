
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
    $(document).ready(function () {
        // 한 화면에 보이는 도서의 개수
        var itemsPerPage = 6;
        // 현재 페이지 번호
        var currentPage = 1;
        // 전체 도서 목록의 개수
        var totalItems = $(".main_prd_li").length;

        // 다음 버튼 클릭 시
        $("#nextPage").click(function () {
            if (currentPage < Math.ceil(totalItems / itemsPerPage)) {
                currentPage++;
            } else {
                currentPage = 1; // 순환 구조: 마지막 페이지에서 다음 버튼 클릭 시 첫 번째 페이지로 이동
            }
            updateDisplay(); // fadeIn 애니메이션 적용
        });

        // 이전 버튼 클릭 시
        $("#prevPage").click(function () {
            if (currentPage > 1) {
                currentPage--;
            } else {
                currentPage = Math.ceil(totalItems / itemsPerPage); // 순환 구조: 첫 번째 페이지에서 이전 버튼 클릭 시 마지막 페이지로 이동
            }
            updateDisplay(); // fadeIn 애니메이션 적용
        });

        // 페이지 업데이트 함수
        function updateDisplay() {
            var startIndex = (currentPage - 1) * itemsPerPage;
            var endIndex = startIndex + itemsPerPage - 1;

            // 모든 도서 숨기기
            $(".main_prd_li").hide();

            // 현재 페이지에 해당하는 도서만 보이게 함
            var currentItems = $(".main_prd_li").slice(startIndex, endIndex + 1);

            // fadeIn 애니메이션 적용
            currentItems.each(function (index) {
                $(this)
                    .delay(index * 100) // 각 항목에 지연을 줘서 순차적으로 애니메이션 적용
                    .fadeIn(700); // fadeIn 애니메이션 적용
            });

            // 버튼 상태 업데이트
            if (currentPage === 1) {
                $("#prevPage").show();
                $("#nextPage").show();
            } else if (currentPage === Math.ceil(totalItems / itemsPerPage)) {
                $("#prevPage").show();
                $("#nextPage").show();
            } else {
                $("#prevPage").show();
                $("#nextPage").show();
            }
        }
        // 페이지 초기화
        updateDisplay(); // 초기에 fadeIn 애니메이션 적용
    });
});






/*window.addEventListener("DOMContentLoaded", function() {
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
});*/


