package scripts

def killJob (jobName, jobNumber) {
	Jenkins.instance.getItemByFullName(jobName).getBuildByNumber(jobNumber).finish(
                        hudson.model.Result.ABORTED,
                        new java.io.IOException("Aborting build")
                );
}

def String giveMeAWorkspace (name, virtualRequired) {
	customWs = env.workspace
	if (virtualRequired) {			
		try {
			retry(5){ // Intentará la operación de busqueda de discos 5 veces
				try{
					println "UNIDADES ACTUALES"
					letter = powershell(returnStdout: true, script: '(Get-PSDrive).Name -match "^[a-z]$"')
					println letter			
					println "UNIDAD VIRTUAL DISPONIBLE "
					letter = powershell(returnStdout: true, script: 'for($j=86;gdr($d=[char]++$j)2>0){}$d')
					println letter
					letter  = letter.substring(0,1)
					println "${letter}"
					println "MONTANDO UNIDAD ${letter}"
					String pathWorkspace = "${env.workspace}"
					pathWorkspace = pathWorkspace.replace(" ", "` ") //para las carpetas que tengan espacio no salga error al crear la unidad virtual
					String functionDisco = "subst ${letter}: ${pathWorkspace}"
					println functionDisco
					powershell(returnStdout: true, script: functionDisco)
					customWs = letter + ":\\" + name.substring(0,2)
				} catch(ex) {					
					// En caso de que encuentre un error, dado que los discos estan ocupados se queda esperando
					sleep(time: 3 ,unit:'MINUTES')
					// Se lanza la excepción para que el retry la capture y vuelva a intentar
					throw ex
				}
			}
		} catch(e) {
				//enviarEmail()
				currentBuild.result = 'FAILURE'		
				throw e	
		}		
	}	
	println "customWs: " + customWs
	return customWs
}


def printSpacer(String message){
    println ("###########################-> ${message} <-###########################")
}

def printSpacerInit(String message){
    println ("###########################-> INICIO - ${message} <-###########################")
}

def printSpacerEnd(String message){
    println ("###########################-> FIN - ${message} <-###########################")
}
