


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
	
	
	
	ArrayList array1 = res.split(",")
	ArrayList array2 = lables.split(",")

	return array1.intersect(array2)
}