(function() {
var app = angular.module('parserApp', []);

app.controller('bodyController', function() {
   this.current = 1;
   this.selectBody = function (newValue){
      this.current = newValue;
   }
   this.isSelected = function (value) {
      return this.current === value;
   }
});
})();
