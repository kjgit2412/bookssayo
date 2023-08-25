/**
* 파일 업로드 콜백 처리
*
* @param files : 업로드 완료된 파일 목록
*/
function fileUploadCallback(files) {
    if (!files || files.length == 0) return;
    const mainImages = document.getElementById("book_main_images");
    const listImages = document.getElementById("book_list_images");
    const tpl = document.getElementById("tpl_image1").innerHTML;

    const domParser = new DOMParser();
    for (const file of files) {
        let html = tpl;
        let target;
        const location = file.location;
        html = html.replace(/\[id\]/g, file.id)
                    .replace(/\[url\]/g, file.fileUrl)
                    .replace(/\[fileName\]/g, file.fileName);
        const dom = domParser.parseFromString(html, "text/html");
        const el = location == 'book_main' || location == 'book_list' ?
                        dom.querySelector(".file_image1") : dom.querySelector("file_item");

        switch(location) {
            case "book_main" : // 메인 이미지
                target = mainImages;
                break;
            case "book_list" : // 목록 이미지
                target = listImages;
                break;
        }

        if (target) target.appendChild(el);
    }

}