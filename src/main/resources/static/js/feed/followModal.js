var followModal = document.getElementById("followModal");
    var followingModal = document.getElementById("followingModal");

    // 모달을 여는 버튼 가져오기
    var openFollowModal = document.getElementById("openFollowModal");
    var openFollowingModal = document.getElementById("openFollowingModal");

    // 모달을 닫는 <span> 요소 가져오기
    var closeFollowModal = document.getElementById("closeFollowModal");
    var closeFollowingModal = document.getElementById("closeFollowingModal");

    // 팔로우 모달 열기
    openFollowModal.onclick = function() {
      followModal.style.display = "block";
    }

    // 팔로잉 모달 열기
    openFollowingModal.onclick = function() {
      followingModal.style.display = "block";
    }

    // 팔로우 모달 닫기
    closeFollowModal.onclick = function() {
      followModal.style.display = "none";
    }

    // 팔로잉 모달 닫기
    closeFollowingModal.onclick = function() {
      followingModal.style.display = "none";
    }

    // 모달 외부를 클릭하면 모달 닫기
    window.onclick = function(event) {
      if (event.target == followModal) {
        followModal.style.display = "none";
      } else if (event.target == followingModal) {
        followingModal.style.display = "none";
      }
    }