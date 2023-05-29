@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        def rig = "WK-H2686"
        def labels_separator = libOtel.getLabels("-l ${labels}")

        def res = libOtel.getIntersection(labels_separator, rig)
        println(res)
    }
    
}
