
$('.toggle-icon').on("click",function(){
    const currentIcon = $(this).text();
    let newIcon;
    if (currentIcon === 'bookmark_border') {
        newIcon = 'bookmark';
      } else if( currentIcon === 'bookmark') {
        newIcon = 'bookmark_border';
      } 
      $(this).text(newIcon);
});

