$(document).ready(function () {
    $('#mainImageInput').on('change', function (event) {
        displayImage(event, '#mainImage');
    });
    $('#topRightImageInput').on('change', function (event) {
        displayImage(event, '#topRightImage');
    });
    $('#bottomRightImageInput').on('change', function (event) {
        displayImage(event, '#bottomRightImage');
    });

    $('.delete-image').on('click', function () {
        var target = $(this).data('target');
        $(target).attr('src', ''); // 이미지 삭제
        $(this).siblings('input[type="file"]').val(''); // 파일 선택 초기화
    });

    function displayImage(event, imageSelector) {
        var file = event.target.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $(imageSelector).attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        }
    }
});