/**
 * File Upload Directive
 */

angular
    .module('lambdaApp')
    .directive('fileread', fileread);

function fileread()  {
    return {
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                var reader = new FileReader();
                var files = changeEvent.target.files;
                console.log(files[0].name);
                console.log(files[0].size);
                console.log("jfjfjf");
                reader.onload = function (loadEvent) {
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;
                    });
                }
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
};
