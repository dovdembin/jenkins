
def getLabels(String str) {
	try { 
		def pattern = /.*-l\s(.*)/
		if(str ==~ pattern) {
			def (word1) = str =~ pattern
			return word1[1].toString().replaceAll(/\\\|/, ",").trim()
		} else {
			return "noMatch"
		}
	} catch(Exception  e1) {
		println("ERROR: in libOtel getLabels")
	}
}


def getTags(String appliance) {
	try {
		def cmd = "curl -s --location '${LABJUNGLE_URL}/api/v1/clsuster/?api_key=${LABJUNGLE_KEY}&name=${appliance}'"
		cmd = cmd + ' | jq  .objects[].tags'
		def tags = sh(script: cmd, returnStdout: true, label: "getTags")
		return tags
	} catch(Exception  e1) {
		println("ERROR: in libOtel getTags")
	}
}


def getIntersection(String lables, String tags) {
	try {
		ArrayList array1 = tags.split(",")
		ArrayList array2 = lables.split(",")
		return array1.intersect(array2)
	} catch(Exception  e1) {
		println("ERROR: in libOtel getIntersection")
	}
}


def getListTags(String labels, String appliance) {
	try {
		def labelsWithCommas = getLabels(labels)
		
		if(appliance.length() > 8) {
			m1 = appliance.substring(0,8)
			m2 = appliance.substring(9,17)
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
	} catch(Exception  e1) {
		println("ERROR: in libOtel getListTags")
	}
}


def getGeneration(String appliance) {
	try {
		def cmd = "curl -s --location '${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}&name=${appliance}'"
		cmd = cmd + ' | jq  .objects[].generation.name'
		def generation_name = sh(script: cmd, returnStdout: true, label: "getGeneration")
		return generation_name.trim()
	} catch(Exception  e1) {
		println("ERROR: in libOtel getGeneration")
	}
}


def getListGenertions(String appliance) {
	try {
		if(appliance.length() > 8) {
			m1 = appliance.substring(0,8)
			m2 = appliance.substring(9,17)
			return [getGeneration(m1),getGeneration(m2)].join(",")
		} else {
			return getGeneration(appliance)
		}
	} catch(Exception  e1) {
		println("ERROR: in libOtel getGeneration")
	}
}