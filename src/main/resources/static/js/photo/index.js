document.getElementById("fileInput").addEventListener("change", function () {
  const file = this.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      const previewImage = document.getElementById("previewImage");
      previewImage.src = e.target.result;
      previewImage.style.display = "block";
    };
    reader.readAsDataURL(file);
  }
});

document
  .getElementById("updateImageInput")
  .addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        const previewImage = document.getElementById("updatePreviewImage");
        previewImage.src = e.target.result;
        previewImage.style.display = "block";
      };
      reader.readAsDataURL(file);
    }
  });

$(document).ready(function () {
  // 카드 클릭 이벤트
  $(".card").click(function (e) {
    e.preventDefault();
    const imgSrc = $(this).find("img").attr("src");
    const imgPhotoFeedNo = $(this).find("photoFeedNo").attr("photoFeedNo");
    $("#modalImage").attr("photoFeedNo",imgSrc);
    $("#modalImage").attr("src", imgSrc);
    $("#imageModal").modal("show");
  });

  // fa-comment 클릭 이벤트
  $(".fa-comment").click(function () {
    $(".comment-section").toggle();
  });

  // fa-pen 클릭 이벤트
  $(".fa-pen").click(function () {
    $("#updateModal").modal("show");
  });

  // 댓글 추가 버튼 클릭 이벤트
  $("#addCommentBtn").click(function () {
    const comment = $("#commentInput").val();
    const commenterImage = "path/to/default-avatar.jpg"; // 기본 아바타 이미지 경로
    const commenterName = "작성자 이름"; // 작성자 이름
    if (comment) {
      const commentHtml = `
        <div class="comment">
          <img src="${commenterImage}" alt="Avatar" class="comment-avatar">
          <div class="comment-content">
            <p class="commenter-name">${commenterName}</p>
            <p class="comment-text">${comment}</p>
          </div>
          <div class="comment-actions">
            <span class="edit-comment">수정</span>
            <span class="delete-comment">삭제</span>
          </div>
        </div>
      `;
      $(".comments-list").append(commentHtml);
      $("#commentInput").val(""); // 입력 필드 초기화
    }
  });

  // 댓글 수정 버튼 클릭 이벤트
  $(document).on("click", ".edit-comment", function () {
    const commentText = $(this).closest(".comment").find(".comment-text");
    const currentText = commentText.text();
    const newText = prompt("댓글을 수정하세요:", currentText);
    if (newText) {
      commentText.text(newText);
    }
  });

  // 댓글 삭제 버튼 클릭 이벤트
  $(document).on("click", ".delete-comment", function () {
    $(this).closest(".comment").remove();
  });
});

$(document).ready(function () {
  $(".fa-ellipsis-vertical").click(function () {
    $(this).siblings().toggleClass("active");
  });
});
