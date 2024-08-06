

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
