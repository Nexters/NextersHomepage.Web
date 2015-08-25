var productController = angular.module('Activites.productController', [

]);
productController.controller('Activites.productController',function ($scope,product) {

  // body...
  //console.log(product);
  //good !!!!
  product.getProductList().then(function (data) {
    console.log(data);
    console.log(data.list);
    $scope.projects=data.list;
  })
  /*console.log("!!!!"+product.getProductList());
  product.test1().then(product.test2).then(function (data) {
    console.log(data);
  })*/
  //$scope.data=product;
})
