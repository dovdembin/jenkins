


def getLabels(String str) {
	def pattern = /.*-l\s(.*)/
	if(str ==~ pattern) {
		def (word1) = str =~ pattern
		return word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}
}

def getIntersection(String lables, String rig) {
	def LABJUNGLE_URL = "http://labjungle.devops.xiodrm.lab.emc.com"
	def LABJUNGLE_KEY = "9703aa016d613b2b21bbb0e6833c3078c811a5d1"
	def labJungle_api="${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}"
	def res = sh(script:"""
							curl -s --location '${labJungle_api}&name=${rig}' | jq  '.objects[].tags'
						""", returnStdout: true, label: "xpool_allocation")
	return res
	def array1 = res.split(",")
	def array2 = lables.split(",")
	def intersection = array1.intersect(array2)
}