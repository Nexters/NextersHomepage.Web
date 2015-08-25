angular.module('carousel',[]).controller('CarouselDemoCtrl', function ($scope) {
  $scope.myInterval = 3000;
  $scope.slides = [
      {
        image: './img/jumbotron/img_main_1.png'
      },
      {
        image: './img/jumbotron/img_main_2.png'
      },
      {
        image: './img/jumbotron/img_main_3.png'
      },
      {
        image: './img/jumbotron/img_main_4.png'
      }
    ];
  //var newWidth = 600 + slides.length + 1;
  /*slides.push({
    image: '../img/jumbotron/img_for_main_1.jpg'
  })
  slides.push({
    image: '../img/jumbotron/img_for_main_2.jpg'
  })*/
  //var slides = $scope.slides = [];
  /*$scope.addSlide = function() {
    var newWidth = 600 + slides.length + 1;
    slides.push({
      image: '../img/jumbotron/img_for_main_1.jpg' + newWidth + '/300',
      text: ['More','Extra','Lots of','Surplus'][slides.length % 4] + ' ' +
        ['Cats', 'Kittys', 'Felines', 'Cutes'][slides.length % 4]
    });
  };
  for (var i=0; i<4; i++) {
    $scope.addSlide();
  }*/
});
