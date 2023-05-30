@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        def rig = "WK-D0089"

	    def labJungle_api="${LABJUNGLE_URL}/api/v1/cluster/?api_key=${LABJUNGLE_KEY}"
        def cmd = "curl -s --location '${labJungle_api}&name=${rig}'"
        def cmd_tags = cmd + " | jq  '.objects[].tags'" 
        def cmd_name = cmd + " | jq  '..objects[].generation.name'"
        def tags = sh(script: cmd_tags, returnStdout: true, label: "xpool_allocation")
        def generation = sh(script: cmd_name, returnStdout: true, label: "xpool_allocation")
        def labels_separator = libOtel.getLabels("-l ${labels}")

        def res = libOtel.getIntersection(labels_separator, tags)
        println(res)
        println(generation )
    }
    
}
