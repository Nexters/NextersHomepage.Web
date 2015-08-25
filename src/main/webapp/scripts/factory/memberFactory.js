
var memberFactory = angular.module('memberFactory', [

]);
memberFactory.factory('member',function ($http,$q,environment) {

  var generlist = {};
  var member = {};

  generlist.getGenerList = function () {

    var deferred = $q.defer();

    $http({
      url: environment.domain+'api/main/memberGenerList.do',
    }).
    success(function(response) {

      console.log("getGenerList: " + response);
      deferred.resolve(response.resData[0].generList);
    }).
    error(function(status) {
      //your code when fails
      console.log(status);
    });

    return deferred.promise;
  }

  generlist.getMemberList = function () {

    var deferred = $q.defer();
    var requestParam = {
      gener : "01"
    }
    $http({
      url: environment.domain+'api/main/memberList.do',
      headers : {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
      method : 'POST',
      data: "gener=" + '02'
    }).
    success(function(response) {
      //console.log("success");
      console.log(response);
      deferred.resolve(response.resData[0].userList);
    }).
    error(function(status) {
      //your code when fails
      console.log(status);
    });

    return deferred.promise;
  }

  return generlist;
})
