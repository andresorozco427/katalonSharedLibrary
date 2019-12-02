def call(Map params = [:]) {

    boolean esTestSuite = params.get('testSuite')
    boolean esTestCollection = params.get('testCollection')

    // obligatorios
    def rutaProyecto = params.get('rutaProyecto')
    def testSuite = params.get('testSuite')

    //opcionales
    def browser = params.get('browser') ?: 'Chrome'
    def enviarReportePorEmail = params.get('enviarReportePorEmail') ?: true
    def publicarReporteEnPipeline = params.get('publicarReporteEnPipeline') ?: true
    //def pathReporte = params.get('pathReporte') ?: ConstantesKatalon.RUTA_REPORTE_POR_DEFECTO + "\\${BUILD_NUMBER}"
    def cantidadReintentos = params.get('cantidadReintentos') ?: 0
    def reintentar = params.get('reintentar') ?: "false"
    def perfil = params.get('perfil') ?: "default"
    
    
    def scriptKatalon = "${KATALON_HOME}\\katalon -noSplash -runMode=console " + " -projectPath=\"" + rutaProyecto + "\""
    scriptKatalon = scriptKatalon + " -retry=${cantidadReintentos} -retryFailedTestCases=${reintentar} -reportFolder=\"" + pathReporte + "\""
    scriptKatalon = scriptKatalon + " -reportFileName=\"${BUILD_NUMBER}-KatalonResult\""
 
    scriptKatalon = scriptKatalon + " -testSuitePath=\"Test Suites/" + testSuite + "\""
    scriptKatalon = scriptKatalon + " -browserType=\"" + browser + "\""
    
    scriptKatalon = scriptKatalon + " -executionProfile=\"" + perfil + "\""

    bat scriptKatalon
}


def checkout_repository(Map params = [:])
{   
    def urlrepositorio = params.urlrepositorio
    def branche = params.branche ?: "master"

    echo "...............CHECKOUT..............."
			  checkout([  
				  	$class: 'GitSCM',
			 	  	branches: [ [ name: '*/${branche}' ] ],
			 	  	doGenerateSubmoduleConfigurations: false,
			 	    extensions: [],
                    submoduleCfg: [],
                     url: params.urlrepositorio])	   
							
			}
}
