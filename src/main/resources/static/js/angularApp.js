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

})();
