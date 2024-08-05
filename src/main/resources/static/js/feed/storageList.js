// scripts.js
document.addEventListener('DOMContentLoaded', function () {
    const carousels = document.querySelectorAll('.carousel');
  
    carousels.forEach(carousel => {
      const track = carousel.querySelector('.carousel-track');
      const slides = Array.from(track.children);
      const nextButton = carousel.querySelector('.carousel-button-right');
      const prevButton = carousel.querySelector('.carousel-button-left');
      const slideWidth = carousel.querySelector('.carousel-slide').offsetWidth;
      const slidesToShow = carousel.classList.contains('photo-carousel') ? 3 : 1; // 사진 슬라이드는 3개, 텍스트 슬라이드는 1개
      const totalSlides = slides.length;
      const totalPages = Math.ceil(totalSlides / slidesToShow); // 전체 페이지 수
      let currentIndex = 0;
  
      const moveToSlide = (track, targetIndex) => {
        const amountToMove = slideWidth * targetIndex;
        track.style.transform = 'translateX(-' + amountToMove + 'px)';
      };
  
      const moveToNextSlide = () => {
        if (currentIndex < totalPages - 1) {
          currentIndex++;
        } else {
          currentIndex = 0; // 마지막 페이지에서 첫 페이지로 이동
        }
        moveToSlide(track, currentIndex);
      };
  
      const moveToPrevSlide = () => {
        if (currentIndex > 0) {
          currentIndex--;
        } else {
          currentIndex = totalPages - 1; // 첫 페이지에서 마지막 페이지로 이동
        }
        moveToSlide(track, currentIndex);
      };
  
      nextButton.addEventListener('click', moveToNextSlide);
      prevButton.addEventListener('click', moveToPrevSlide);
    });
  });
  