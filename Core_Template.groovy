@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        // def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        // def appliance = "WK-D0089"
        def labels = "EX\\|MLK\\|Riptide,CopperBlade\\|LightBlade"
        def appliance = "RT-D0201-RT-D0197-federation-TAG"
        def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
        if(appliance ==~ pattern) {
            def labels_separator = libOtel.getLabels("-l ${labels}")
            libOtel.getFederation(labels_separator, appliance)
            def (res1) = appliance =~ pattern
		    m1 = res1[1]
		    m2 = res1[2]
            println(libOtel.getGeneration(m1) )
            println(libOtel.getGeneration(m2) )
        } else {
            def tags = libOtel.getTags(appliance)
            def labels_separator = libOtel.getLabels("-l ${labels}")
            def intersection = libOtel.getIntersection(labels_separator, tags)
            println(intersection)
            println(libOtel.getGeneration(appliance) )
        }
    }
    
}
