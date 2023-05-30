@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         
        // def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        // def appliance = "WK-D0089"

        def labels = "Stratus,VirtualLG"
        def appliance = "ST-H1008"
        def labels_separator = libOtel.getLabels("-l ${labels}")
        def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
        def generation
        def commonLabels
        if(appliance ==~ pattern) {
            def appliances =  libOtel.getFederation(appliance)
            def tags1 = libOtel.getTags(appliances[0])
            def tags2 = libOtel.getTags(appliances[1])
            def intersection1 = libOtel.getIntersection(labels_separator, tags1)
            def intersection2 = libOtel.getIntersection(labels_separator, tags2)
            intersection_commas = intersection1.join(",") + intersection2.join(",")
            generation="EX"
        } else {
            def tags = libOtel.getTags(appliance)
            def intersection = libOtel.getIntersection(labels_separator, tags)
            intersection_commas = intersection.join(",")
            generation = libOtel.getGeneration(appliance)
        }

        sh(script: """
        docker run --rm -e OTEL_EXPORTER_OTLP_ENDPOINT \
            dell/opentelemetry-cli:0.4.0 \
            metric counter tridevlab.test-counter \
            -a "str[]:test.my-array=${intersection_commas}" \
            -a "test.generation=${generation}"
    """, label: "Report OTel", returnStatus: true)
    }
    
}
