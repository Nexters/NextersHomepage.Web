
var productFactory = angular.module('productFactory', [

]);
productFactory.factory('product',function ($http,$q,environment) {
  var product = {};


  product.getProductList = function () {

    var deferred = $q.defer();

    $http({
      url: environment.domain+'api/main/getProjectList.do',
    }).
    success(function(response) {

      console.log(response);
      deferred.resolve(response.resData[0]);
    }).
    error(function(status) {
      //your code when fails
      console.log(status);
    });

    return deferred.promise;

  }

  product.test1 = function () {
    var deferred = $q.defer();
    setTimeout(function () {
      deferred.resolve("success resolve");
    },2000)
    return deferred.promise;
  }



  product.test2 = function (data) {
    var deferred = $q.defer();
    setTimeout(function () {
      deferred.resolve(data+"success resolve");
    },1000)
    return deferred.promise;
  }


  return product;
})
