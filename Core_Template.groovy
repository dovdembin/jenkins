@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        def rig = "WK-D0089"
	    def labJungle_api="${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}"
        def cmd = "curl -s --location '${labJungle_api}&name=${rig}' | jq  '.objects[].tags'"
        def tags = sh(script: cmd, returnStdout: true, label: "xpool_allocation")
        def labels_separator = libOtel.getLabels("-l ${labels}")

        def res = libOtel.getIntersection(labels_separator, tags)
        println(res)
    }
    
}
