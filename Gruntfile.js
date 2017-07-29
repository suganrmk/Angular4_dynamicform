// module.exports = function(grunt) {
//
//   grunt.initConfig({
// 	pkg: grunt.file.readJSON('package.json'),
//
//
// 	concat: {
// 		options: {
// 		  separator: ';'
// 		},
// 		dist: {
// 	      src: ['src/assets/src/js/*.js'],
// 		  dest: 'src/assets/dist/js/main.js'
// 		}
// 	},
//
//      sass: {
//     dist: {
//       files: {
//         'src/assets/dist/css/main.css': 'src/assets/src/scss/main.scss'
//       }
//     }
//   },
//
//     jshint: {
//       files: ['Gruntfile.js', 'src/assets/src/js/*.js'],
//       options: {
//         globals: {
//           jQuery: true
//         }
//       }
//     },
//     watch: {
//       files: ['<%= jshint.files %>' , 'src/assets/src/scss/main.scss' , 'src/assets/src/scss/**/*.scss'],
//       tasks: ['jshint' , 'sass']
//     }
//   });
//
//   grunt.loadNpmTasks('grunt-contrib-jshint');
//   grunt.loadNpmTasks('grunt-contrib-watch');
//   grunt.loadNpmTasks('grunt-contrib-concat');
//   grunt.loadNpmTasks('grunt-contrib-sass');
//   grunt.registerTask('default', ['jshint' , 'concat', 'sass:dist' , 'watch']);
// };
// Gruntfile.js

// our wrapper function (required by grunt and its plugins)
// all configuration goes inside this function
module.exports = function(grunt) {

  // URI paths for our tasks to use.
  grunt_uriSrc = 'src/main/webapp/resources/';
  grunt_uriDist = grunt_uriSrc + '/dist/';

  // ===========================================================================
  // CONFIGURE GRUNT ===========================================================
  // ===========================================================================
  grunt.initConfig({

    // get the configuration info from package.json ----------------------------
    // this way we can use things like name and version (pkg.name)
    pkg: grunt.file.readJSON('package.json'),

    // all of our configuration will go here
    // configure jshint to validate js files -----------------------------------
    sass: { // Task
      dist: { // Target
        options: { // Target options
          style: 'expanded'
        },
        files: { // Dictionary of files
          'src/main/webapp/resources/sass_compiled/login.css': 'src/main/webapp/resources/sass/login.scss', // 'destination': 'source'
          'src/main/webapp/resources/sass_compiled/main.css': 'src/main/webapp/resources/sass/main.scss',
          'src/main/webapp/resources/sass_compiled/vendors.css': 'src/main/webapp/resources/sass/vendors.scss',
          'src/main/webapp/resources/sass_compiled/theme.css': 'src/main/webapp/resources/sass/themes/theme.scss',
          'src/main/webapp/resources/sass_compiled/skin-red.css': 'src/main/webapp/resources/sass/themes/skin-red.scss',
          'src/main/webapp/resources/sass_compiled/responsive.css': 'src/main/webapp/resources/sass/responsive/responsive.scss',
          // PTD Source Starts
          'src/assets/dist/css/main.css': 'src/assets/src/scss/main.scss'
        }
      }
    },
    cssmin: {
      options: {
        banner: '/*\n <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> \n*/\n'
      },
      dev: {
        files: {
          'src/main/webapp/resources/css/login.min.css': [
            grunt_uriSrc + '/sass_compiled/login.css',
            grunt_uriSrc + '/css/responsive.css'
          ],
          'src/main/webapp/resources/css/vendors.min.css': [grunt_uriSrc + '/sass_compiled/vendors.css'],
          'src/main/webapp/resources/css/prft.min.css': [
            grunt_uriSrc + '/sass_compiled/main.css',
            grunt_uriSrc + '/sass_compiled/theme.css',
            grunt_uriSrc + '/sass_compiled/skin-red.css',
            grunt_uriSrc + '/sass_compiled/responsive.css'
          ]
        }
      }
    },
    concat: {
      dev: {
        files: {
          'src/main/webapp/resources/js/dev/login.js': [
            grunt_uriSrc + '/js/lib/jquery.js',
            grunt_uriSrc + '/js/lib/jquery.cookie.js',
            grunt_uriSrc + 'js/lib/bootstrap.js',
            grunt_uriSrc + '/js/lib/bowser.min.js',
            grunt_uriSrc + 'js/hash/aes.js',
            grunt_uriSrc + '/js/hash/pbkdf2.js',
            grunt_uriSrc + '/js/lib/jquery-confirm.js',
            grunt_uriSrc + 'js/login/login.js'
          ],
          'src/main/webapp/resources/js/dev/common-lib.js': [
            grunt_uriSrc + '/js/lib/jquery.js',
            grunt_uriSrc + '/js/lib/jquery.cookie.js',
            grunt_uriSrc + '/js/lib/moment.min.js',
            grunt_uriSrc + '/js/lib/jquery-ui.js',
            grunt_uriSrc + '/js/lib/angular.js',
            grunt_uriSrc + '/js/lib/bootstrap.js',
            grunt_uriSrc + '/js/lib/jquery.dataTables.js',
            grunt_uriSrc + '/js/lib/angular-datatables.js',
            grunt_uriSrc + '/js/lib/datatables.buttons.js',
            grunt_uriSrc + '/js/lib/dataTables.tableTools.js',
            grunt_uriSrc + '/js/lib/bootstrap-calendar.min.js',
            grunt_uriSrc + '/js/lib/angular-bootstrap-calendar-tpls.js',
            grunt_uriSrc + '/js/lib/angular-animate.js',
            grunt_uriSrc + '/js/lib/angular-messages.js',
            grunt_uriSrc + '/js/lib/angular-sanitize.js',
            grunt_uriSrc + '/js/lib/angularjs-dropdown-multiselect.js',
            grunt_uriSrc + '/js/lib/ui-bootstrap-tpls-1.3.2.js',
            grunt_uriSrc + '/js/lib/select.js',
            grunt_uriSrc + '/js/lib/jquery-confirm.js',
            grunt_uriSrc + '/js/lib/nya-bs-select.min.js',
            grunt_uriSrc + '/js/lib/bowser.min.js',
            grunt_uriSrc + '/js/lib/Chart.min.js',
            grunt_uriSrc + '/js/lib/angular-chart.js',
            grunt_uriSrc + 'js/lib/sly-plugin.js'
          ],
          'src/main/webapp/resources/js/dev/lib.js': [
            grunt_uriSrc + '/js/lib/angular-route.js',
            grunt_uriSrc + '/js/lib/angular-resource.js',
            grunt_uriSrc + '/js/lib/angular-datatables.util.js',
            grunt_uriSrc + '/js/lib/angular-datatables.options.js',
            grunt_uriSrc + '/js/lib/angular-datatables.instances.js',
            grunt_uriSrc + '/js/lib/angular-datatables.factory.js',
            grunt_uriSrc + '/js/lib/angular-datatables.renderer.js',
            grunt_uriSrc + '/js/lib/angular-datatables.directive.js',
            grunt_uriSrc + '/js/lib/angular-bootstrap.js',
            grunt_uriSrc + '/js/lib/angular-datatables.bootstrap.options.js',
            grunt_uriSrc + '/js/lib/angular-datatables.bootstrap.tabletools.js',
            grunt_uriSrc + '/js/lib/angular-datatables.bootstrap.colvis.js',
            grunt_uriSrc + '/js/lib/datatables.columnfilter.js',
            grunt_uriSrc + '/js/lib/angular-datatables.columnfilter.js',
            grunt_uriSrc + '/js/lib/angular-datatables.fixedcolumns.js',
            grunt_uriSrc + '/js/lib/datatables.fixedcolumns.js',
            grunt_uriSrc + '/js/lib/angular-datatables.responsive.js',
            grunt_uriSrc + '/js/lib/angular-datatables.buttons.js',
            grunt_uriSrc + '/js/lib/angular-datatables.buttons.html5.js',
            grunt_uriSrc + '/js/lib/angular-datatables.buttons.print.js',
            grunt_uriSrc + '/js/lib/angular-datatables.buttons.colVis.js',
            grunt_uriSrc + '/js/lib/angular-datatables.buttons.flash.js',
            grunt_uriSrc + 'js/hash/aes.js',
            grunt_uriSrc + '/js/hash/pbkdf2.js',
            grunt_uriSrc + 'js/hash/aesutil.js'
          ],
          'src/main/webapp/resources/js/dev/prft.js': [
            grunt_uriSrc + 'js/app/admin_lte.js',
            grunt_uriSrc + 'js/app/common.js',
            grunt_uriSrc + 'js/constants/url.js',
            grunt_uriSrc + 'js/constants/utils.js',
            grunt_uriSrc + 'js/directive/perfdirective.js',
            grunt_uriSrc + 'js/service/commonServices.js',
            grunt_uriSrc + 'js/controller/abstractController.js',
            grunt_uriSrc + 'js/controller/datatableController.js',
            grunt_uriSrc + 'js/controller/menuController.js',
            grunt_uriSrc + 'js/service/notificationServices.js',
            grunt_uriSrc + 'js/controller/notificationController.js',
            grunt_uriSrc + 'js/controller/homeController.js',
            grunt_uriSrc + 'js/controller/headerController.js',
            grunt_uriSrc + 'js/controller/profileController.js',
            grunt_uriSrc + 'js/service/profileServices.js',
            grunt_uriSrc + 'js/controller/employeeController.js',
            grunt_uriSrc + 'js/service/employeeServices.js',
            grunt_uriSrc + 'js/controller/dashboardController.js',
            grunt_uriSrc + 'js/service/dashboardServices.js',
            grunt_uriSrc + 'js/controller/designationController.js',
            grunt_uriSrc + 'js/service/designationServices.js',
            grunt_uriSrc + 'js/controller/projectController.js',
            grunt_uriSrc + 'js/service/projectServices.js',
            grunt_uriSrc + 'js/controller/rolesController.js',
            grunt_uriSrc + 'js/service/rolesServices.js',
            grunt_uriSrc + 'js/controller/projectMembersController.js',
            grunt_uriSrc + 'js/service/projectMembersServices.js',
            grunt_uriSrc + 'js/controller/importPtoController.js',
            grunt_uriSrc + 'js/service/importPtoServices.js',
            grunt_uriSrc + 'js/controller/leaveController.js',
            grunt_uriSrc + 'js/service/leaveServices.js',
            grunt_uriSrc + 'js/controller/reportsJobtitleController.js',
            grunt_uriSrc + 'js/service/reportsJobtitleServices.js',
            grunt_uriSrc + 'js/controller/resetPwdController.js',
            grunt_uriSrc + 'js/controller/leaveReportsController.js',
            grunt_uriSrc + 'js/controller/ticketsController.js',
            grunt_uriSrc + 'js/service/ticketServices.js',
            grunt_uriSrc + 'js/controller/ticketsReportController.js',
            grunt_uriSrc + 'js/controller/emprolesController.js',
            grunt_uriSrc + 'js/service/empRolesServices.js',
            grunt_uriSrc + 'js/service/holidaysServices.js',
            grunt_uriSrc + 'js/controller/holidaysController.js',
            grunt_uriSrc + 'js/service/reportEbsServices.js',
            grunt_uriSrc + 'js/controller/reportsEbsController.js'
          ],
          'src/main/webapp/resources/css/vendors.css': grunt_uriSrc + 'sass_compiled/vendors.css',
          'src/main/webapp/resources/css/perf.css': grunt_uriSrc + 'sass_compiled/main.css',
          'src/main/webapp/resources/css/theme.css': grunt_uriSrc + 'sass_compiled/theme.css',
          'src/main/webapp/resources/css/skin-red.css': grunt_uriSrc + 'sass_compiled/skin-red.css',
          'src/main/webapp/resources/css/responsive.css': grunt_uriSrc + 'sass_compiled/responsive.css',
          'src/assets/src/js/main.js' :'src/assets/dist/js/main.js' // PTD Source
        }
      }
    },
    uglify: {
      options: {
        banner: '/*\n <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> \n*/\n'
      },
      dev: {
        files: {
          'src/main/webapp/resources/js/dev/login.min.js': [grunt_uriSrc + 'js/dev/login.js'],
          'src/main/webapp/resources/js/dev/common-lib.min.js': [grunt_uriSrc + '/js/dev/common-lib.js'],
          'src/main/webapp/resources/js/dev/lib.min.js': [grunt_uriSrc + '/js/dev/lib.js'],
          'src/main/webapp/resources/js/dev/prft.min.js': [grunt_uriSrc + '/js/dev/prft.js']
        }
      }
    },
    jshint: {
      files: ['Gruntfile.js', 'src/assets/src/js/*.js'],
      options: {
        globals: {
          jQuery: true
        }
      }
    },
    watch: {
      files: ['<%= jshint.files %>' , 'src/assets/src/scss/main.scss' , 'src/assets/src/scss/**/*.scss'],
      tasks: ['jshint' , 'sass']
    },
  	exec: {
  		PTD_install: "npm install",
  		PTD_run: "npm start",
  		PTD_build: "ng build --output-path=./src/main/webapp/ptd/"
  	}
  });

  grunt.registerTask('ptd-run','Starting development server', function() {
    grunt.log.writeln('===============| STARTING PTD SERVER |===============');
    grunt.task.run("exec:PTD_run");
  });

  grunt.registerTask('ptd-build','production build creation', function() {
	    grunt.log.writeln('===============| INITIATING BUILD PROCESS |===============');
	    grunt.task.run("exec:PTD_build");
  });
  grunt.registerTask('ptd-install','Build production version of PTD', function() {
	    grunt.log.writeln('===============| INSTALLING DEPENDENCIES |===============');
	    grunt.task.run("exec:PTD_install");
  });
  // ============= // CREATE TASKS ========== //
  grunt.registerTask('default', ['sass', 'cssmin', 'concat', 'jshint','watch']);

  //this task will only run the dev configuration
  grunt.registerTask('dev', ['sass', 'cssmin', 'concat:dev', 'uglify:dev']);
  //  grunt.registerTask('dev', ['sass', 'cssmin', 'concat:dev']);

  // ===========================================================================
  // LOAD GRUNT PLUGINS ========================================================
  // ===========================================================================
  // we can only load these if they are in our package.json
  // make sure you have run npm install so our app can find these
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks("grunt-exec");


};
