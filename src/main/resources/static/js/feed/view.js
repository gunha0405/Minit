
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
      } 
      $(this).text(newIcon);
});

