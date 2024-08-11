function feedUpdate(userFeedNo, userFeedWriter){
			swal({
                title:"게시글 수정",
                text :"게시글을 수정하시겠습니까?",
                icon :"info",
                buttons:{
                    cancel:{
                        text:"취소",
                        value:false,
                        visible:true,
                        className:"btn-secondary",
                        closeModal:true
                    },
                    confirm:{
                        text:"확인",
                        value:true,
                        visible:true,
                        className:"btn-point",
                        closeModal:true
                    }
                }
            }).then(function(isConfirm){
                if(isConfirm){
                	console.log(userFeedNo);
                	console.log(userFeedWriter);
                    location.href="/feed/updateFrm?userFeedWriter="+userFeedWriter+"&userFeedNo="+userFeedNo;
                }   
            });
		}
		
		function feedDelete(userFeedNo) {
			swal({
                title:"게시글 삭제",
                text :"게시글을 삭제하시겠습니까?",
                icon :"warning",
                buttons:{
                    cancel:{
                        text:"취소",
                        value:false,
                        visible:true,
                        className:"btn-secondary",
                        closeModal:true
                    },
                    confirm:{
                        text:"삭제",
                        value:true,
                        visible:true,
                        className:"btn-point",
                        closeModal:true
                    }
                }
            }).then(function(isConfirm){
                if(isConfirm){
                    location.href="/feed/delete?userFeedNo="+userFeedNo;
                }   
            });
		}
		
    document.addEventListener('DOMContentLoaded', () => {
        const slides = document.querySelector('.slides-img');
        const slideCount = document.querySelectorAll('.slide-img').length;
        let index = 0;

        function showSlide(newIndex) {
            if (newIndex >= slideCount) {
                index = 0;
            } else if (newIndex < 0) {
                index = slideCount - 1;
            } else {
                index = newIndex;
            }
            slides.style.transform = `translateX(${-index * 100}%)`;
        }

        function prevSlide() {
            showSlide(index - 1);
        }

        function nextSlide() {
            showSlide(index + 1);
        }

        // Assign functions to buttons
        document.querySelector('.prev-img').addEventListener('click', prevSlide);
        document.querySelector('.next-img').addEventListener('click', nextSlide);
    });
    
    
$('.toggle-icon').on("click",function(){
    const currentIcon = $(this).text();
    let newIcon;
    if (currentIcon === 'bookmark_border') {
        newIcon = 'bookmark';
      } else if( currentIcon === 'bookmark') {
        newIcon = 'bookmark_border';
      } else if(currentIcon === 'favorite_border') {
        newIcon = 'favorite';
      } else if(currentIcon === 'favorite') {
        newIcon = 'favorite_border';
      } else if(currentIcon === 'report_gmailerrorred') {
        newIcon = 'report';
      } else if(currentIcon === 'report') {
        newIcon = 'report_gmailerrorred';
      } 
      $(this).text(newIcon);
});

