@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        // def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        // def appliance = "WK-D0089"
        def labels = "EX\\|MLK\\|Riptide,CopperBlade\\|LightBlade"
        def appliance = "RT-D0201-RT-D0197-federation-TAG"
        def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
        if(appliance ==~ pattern) {

        } else {
            def tags = libOtel.getTags(appliance)
            def labels_separator = libOtel.getLabels("-l ${labels}")
            def intersection = libOtel.getIntersection(labels_separator, tags)
            println(intersection)
            println(libOtel.getGeneration(appliance) )
        }
    }
    
}
