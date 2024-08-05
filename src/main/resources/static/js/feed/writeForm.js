// $("#noticeContent").summernote({
//     height:500,
//     lang : "ko-KR",
//     disableResize : true,
//     disableResizeEditor : true,
//     resize : false,
//     callbacks :{
//         //사용자가 이미지를 올리면 동작할 함수 지정
//         onImageUpload : function(files){//이미지 올리면 이렇게 행동 하면 되
//             //이미지를 선택하면 해당 파일을 매개변수로 받음
//             //이미지를 처리할 함수를 실행 -> 이때 매개변수로 첨부한 파일, 에디터객체를 전송
//             uploadImage(files[0],this)

//         }
//     }
// });

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

// $("#noticeContent").summernote({
//     height:500,
//     lang : "ko-KR",
//     disableResize : true,
//     disableResizeEditor : true,
//     resize : false,
//     callbacks :{
//         //사용자가 이미지를 올리면 동작할 함수 지정
//         onImageUpload : function(files){//이미지 올리면 이렇게 행동 하면 되
//             //이미지를 선택하면 해당 파일을 매개변수로 받음
//             //이미지를 처리할 함수를 실행 -> 이때 매개변수로 첨부한 파일, 에디터객체를 전송
//             uploadImage(files[0],this);
//         }
//     }
// });

$('#noticeContent').summernote({
    // 에디터 높이
    height: 500,
    // 에디터 한글 설정
    lang: "ko-KR",
    // 에디터에 커서 이동 (input창의 autofocus라고 생각하시면 됩니다.)
    focus : true,
    toolbar: [
          // 글꼴 설정
          ['fontname', ['fontname']],
          // 글자 크기 설정
          ['fontsize', ['fontsize']],
          // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
          ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
          // 글자색
          ['color', ['forecolor','color']],
          // 표만들기
          ['table', ['table']],
          // 글머리 기호, 번호매기기, 문단정렬
          ['para', ['ul', 'ol', 'paragraph']],
          // 줄간격
          ['height', ['height']],
          // 그림첨부, 링크만들기, 동영상첨부
          //['insert',['picture','link','video']],
          // 코드보기, 확대해서보기, 도움말
          //['view', ['codeview','fullscreen', 'help']]
        ],
        // 추가한 글꼴
      fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
       // 추가한 폰트사이즈
      fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
      
  });