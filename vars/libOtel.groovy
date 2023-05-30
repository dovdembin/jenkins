def labJungle_api="${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}"

def getLabels(String str) {
	def pattern = /.*-l\s(.*)/
	if(str ==~ pattern) {
		def (word1) = str =~ pattern
		return word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}
}

def getIntersection(String lables, String tags) {
	ArrayList array1 = tags.split(",")
	ArrayList array2 = lables.split(",")
	return array1.intersect(array2)
}

def getGeneration(String appliance) {
	def cmd = "curl -s --location '${labJungle_api}&name=${appliance} | jq  '.objects[].generation.name'"
	def generation_name = sh(script: cmd, returnStdout: true, label: "xpool_allocation")
	return generation_name
}

def getTags(String appliance) {
	def cmd = "curl -s --location '${labJungle_api}&name=${appliance} | jq  '.objects[].tags'"
	def tags = sh(script: cmd, returnStdout: true, label: "xpool_allocation")
	return tags
}
