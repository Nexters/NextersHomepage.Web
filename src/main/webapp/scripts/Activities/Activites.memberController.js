var memberController = angular.module('Activites.memberController', [

]);
memberController.controller('Activites.memberController',function ($scope,member) {

  // body...
  //console.log(product);
  //good !!!
  //console.log(member);
  member.getGenerList().then(function (data) {

    member.getMemberList().then(function (data) {
      console.log(data);
      $scope.members=data;
    })
    $scope.geners=data;
  })
  
})
