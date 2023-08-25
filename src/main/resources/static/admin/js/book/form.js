/**
* 파일 업로드 콜백 처리
*
* @param files : 업로드 완료된 파일 목록
*/
function fileUploadCallback(files) {
    if (!files || files.length == 0) return;
    const mainImages = document.getElementById("book_main_images");
    const listImages = document.getElementById("book_list_images");
    const editorImages = document.getElementById("book_editor_images");

    const tpl = document.getElementById("tpl_image1").innerHTML;
    const tpl2 = document.getElementById("tpl_editor").innerHTML;

    const domParser = new DOMParser();
    for (const file of files) {
        const location = file.location;
        let html = location == 'editor'? tpl2 : tpl;
        let target;
        html = html.replace(/\[id\]/g, file.id)
                    .replace(/\[url\]/g, file.thumbsUrl[0])
                    .replace(/\[orgUrl\]/g, file.fileUrl)
                    .replace(/\[fileName\]/g, file.fileName);
        const dom = domParser.parseFromString(html, "text/html");
        const el = location == 'main' || location == 'list' ?
                        dom.querySelector(".file_images") : dom.querySelector(".file_items");

        switch(location) {
            case "main" : // 메인 이미지
                target = mainImages;
                break;
            case "list" : // 목록 이미지
                target = listImages;
                break;
            case "editor" : // 상세설명 에디터 이미지
                target = editorImages;
                const imgTag = `<img src='${file.fileUrl}'>`;
                CKEDITOR.instances.description.insertHtml(imgTag);

                const insertEditorEl = dom.querySelector(".insert_editor");
                insertEditorEl.addEventListener("click", function() {
                    insertEditor(this.dataset.url);
                });

                break;
        }

        if (target) target.appendChild(el);
    }
}

/**
* 파일 삭제 성공시 콜백 처리
*
* @param fileId : 파일 등록 번호
*/
function fileDeleteCallback(fileId) {
    if (!fileId) return;

    const el = document.getElementById(`file_${fileId}`);
    if (el) el.parentElement.removeChild(el);
}

/**
* 이미지 에디터 본문 첨부
*
*/
function insertEditor(url) {
    const imgTag = `<img src='${url}'>`;
    CKEDITOR.instances.description.insertHtml(imgTag);
}

/** 이벤트 처리 */
window.addEventListener("DOMContentLoaded", function() {
    CKEDITOR.replace("description", {
        height: 350
    });

    const insertEditors = document.getElementsByClassName("insert_editor");
    for (const el of insertEditors) {
        el.addEventListener("click", function() {
            insertEditor(this.dataset.url);
        });
    }
});