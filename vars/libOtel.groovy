
def getLabels(String str) {
	def pattern = /.*-l\s(.*)/
	if(str ==~ pattern) {
		def (word1) = str =~ pattern
		return word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}
}


def getTags(String appliance) {
	def cmd = "curl -s --location '${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}&name=${appliance}'"
	cmd = cmd + ' | jq  .objects[].tags'
	def tags = sh(script: cmd, returnStdout: true, label: "getTags")
	return tags
}


def getIntersection(String lables, String tags) {
	ArrayList array1 = tags.split(",")
	ArrayList array2 = lables.split(",")
	return array1.intersect(array2)
}

@NonCPS
def getListTags(String labels, String appliance) {
	def labelsWithCommas = getLabels(labels)
	def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
	if(appliance ==~ pattern){
		def (res1) = appliance =~ pattern
		def tags1 = getTags(res1[1])
        def intersection1 = getIntersection(labelsWithCommas, tags1)
		def tags2 = getTags(res1[2])
        def intersection2 = getIntersection(labelsWithCommas, tags2)
		ArrayList combine = intersection1 + intersection2
		return combine.join(",")
	} else {
		def tags = getTags(appliance)
        def intersection = getIntersection(labelsWithCommas, tags)
        return intersection.join(",")
	}
}


def getGeneration(String appliance) {
	def cmd = "curl -s --location '${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}&name=${appliance}'"
	cmd = cmd + ' | jq  .objects[].generation.name'
	def generation_name = sh(script: cmd, returnStdout: true, label: "getGeneration")
	return generation_name
}

@NonCPS
def getListGenertions(String appliance) {
	def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
	if(appliance ==~ pattern){
		def (res1) = appliance =~ pattern
		return [getGeneration(res1[1]),getGeneration(res1[2])].join(",")
	} else {
		return getGeneration(appliance)
	}
}