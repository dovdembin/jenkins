
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
	def lablesUppercase = lables.toUpperCase()
	def tagsUppercaseText = tags.toUpperCase()
	ArrayList array2 = lablesUppercase.split(",")
	ArrayList array1 = tagsUppercaseText.split(",")
	return array1.intersect(array2)
}


def getListTags(String labels, String appliance) {
	def labelsWithCommas = getLabels(labels)
	if(appliance.contains("-")) {
		if(appliance.length() > 8) {
			def m1 = appliance.substring(0,8)
			def m2 = appliance.substring(9,17)
			def tags1 = getTags(m1)
			def intersection1 = getIntersection(labelsWithCommas, tags1)
			def tags2 = getTags(m2)
			def intersection2 = getIntersection(labelsWithCommas, tags2)
			ArrayList combine = intersection1 + intersection2
			return combine.join(",")
		} else {
			def tags = getTags(appliance)
			def intersection = getIntersection(labelsWithCommas, tags)
			return intersection.join(",")
		}
	}
}


def getGeneration(String appliance) {
	def cmd = "curl -s --location '${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}&name=${appliance}'"
	cmd = cmd + ' | jq  .objects[].generation.name'
	def generation_name = sh(script: cmd, returnStdout: true, label: "getGeneration")
	return generation_name.trim()
}


def getListGenertions(String appliance) {
	if(appliance.contains("-")) {
		if(appliance.length() > 8) {
			def m1 = appliance.substring(0,8)
			def m2 = appliance.substring(9,17)
			return [getGeneration(m1),getGeneration(m2)].join(",")
		} else {
			return getGeneration(appliance)
		}
	}
}
