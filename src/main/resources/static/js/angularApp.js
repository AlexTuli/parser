(function() {
var app = angular.module('parserApp', []);

app.controller('bodyController', function() {
    this.current = 1;
   this.selectBody = function (newValue){
      this.current = newValue;
   };
   this.isSelected = function (value) {
      return this.current === value;
   }
});

    app.controller('parserController', [ '$http', function($http){
        var vm = this;
        vm.answer = [];
        vm.myForm = {};
        vm.makeRequest = function(){
            $http({
                method: "POST",
                url: "/sax",
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
        vm.update = function(){
            vm.makeRequest();
        };


    }]);

    app.controller('saxParserRequestController', [ '$http', function($http){
        var vm = this;
        vm.answer = [];
        $http({
            method: "GET",
            url: "/sax"
        }).then (function successCallback (responce) {
            vm.answer = responce.data;
    }, function errorCallback (responce) {
        vm.answer = responce.statusText;
    });

    }]);

    app.controller('parserController', function(){
        this.typeOfParser = "sax";
        this.setTypeOfParser = function(newType){
            this.typeOfParser = newType;
        }
    });
    //This is transform JSON into something pretty :D
    app.filter('pretty', function(){
        return function (json) {
            return JSON.stringify(json, undefined, 2)
        }
    });

})();
