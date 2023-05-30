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

def checkLabels(String text, String appliance) {
	def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
	def m1
	def m2
	if(appliance ==~ pattern){
		def (res1) = appliance =~ pattern
		m1 = res1[1]
		m2 = res1[2]
	}
	else {
		return getLabels(text, appliance)
	}
	ArrayList list1 = getLabels(text, m1).split(",")
	ArrayList list2 = getLabels(text, m2).split(",")
	def createdList = list1 + list2
	return createdList.unique().join(",")
}
