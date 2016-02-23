(function() {
var app = angular.module('parserApp', []);

app.controller('bodyController', function() {
    this.current = 1;
    this.parserType = 'sax';
   this.selectBody = function (newValue){
      this.current = newValue;
   };
   this.isSelected = function (value) {
      return this.current === value;
   };
    this.selectParser = function (newValue) {
        this.parserType = newValue;
    };

});

    app.controller('parserController', [ '$http', function($http){
        var vm = this;
        vm.answer = [];
        vm.myForm = {};
        vm.makeRequest = function(){
            $http({
                method: "POST",
                url: "/parser",
                headers: {
                    'Content-Type': undefined
                },
                data: vm.myForm
            }).then(function successCallback(response) {
                vm.answer = response.data;
            }, function errorCallback(response) {
                vm.answer = response.statusText;
            });
        };

    }]);

    //This is transform JSON into something pretty
    app.filter('pretty', function(){
        return function (json) {
            return JSON.stringify(json, undefined, 2)
        }
    });

    app.directive('navigationBar', function(){
        return {
            restrict: 'E',
            templateUrl: 'template/navigation-bar.html'
        };
    });

    app.directive('parserForm', function(){
       return{
           restrict: 'E',
           templateUrl: 'template/parser-form.html',
           controller:function(){
               var vm = this;
               vm.answer = [];
               vm.myForm = {};
               vm.makeRequest = function(){
                   $http({
                       method: "POST",
                       url: "/parser",
                       headers: {
                           'Content-Type': undefined
                       },
                       data: vm.myForm
                   }).then(function successCallback(response) {
                       vm.answer = response.data;
                   }, function errorCallback(response) {
                       vm.answer = response.statusText;
                   });
               };
           },
           controllerAs: 'parser'
       }
    });

})();
