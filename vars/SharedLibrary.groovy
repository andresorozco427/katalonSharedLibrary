import constantes.ConstantesKatalon

def call(Map params = [:]) {

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
    def perfil = params.get('perfil') ?: ConstantesKatalon.PERFIL_DEFECTO_KATALON
    
    def scriptKatalon = "${KATALON_HOME}\\katalon -noSplash -runMode=console " + " -projectPath=\"" + rutaProyecto + "\""
    scriptKatalon = scriptKatalon + " -retry=${cantidadReintentos} -retryFailedTestCases=${reintentar} -reportFolder=\"" + pathReporte + "\""
    scriptKatalon = scriptKatalon + " -reportFileName=\"${BUILD_NUMBER}-KatalonResult\""
    scriptKatalon = scriptKatalon + " -testSuitePath=\"Test Suites/" + testSuite + "\""
    scriptKatalon = scriptKatalon + " -browserType=\"" + browser + "\""
    
    scriptKatalon = scriptKatalon + " -executionProfile=\"" + perfil + "\""
    scriptKatalon = scriptKatalon + " --config -proxy.option=MANUAL_CONFIG -proxy.server.type=HTTPS -proxy.server.address=\"http://proxy.epm.com.co\" -proxy.server.port=\"8080\""

    bat scriptKatalon
}
