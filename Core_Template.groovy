@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         
        // def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        // def appliance = "WK-D0089"

        def labels = "EX\\|MLK,NVMeOF-FC,PhysicalLG,fc"
        def appliance = "WX-D1319"
        def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
        def generation
        def commonLabels
        if(appliance ==~ pattern) {
            def labels_separator = libOtel.getLabels("-l ${labels}")
            def map =  libOtel.getFederation(labels_separator, appliance)
            // println(map.intersection)
            // println(libOtel.getGeneration(map.m1))
            // println(libOtel.getGeneration(map.m2))
        } else {
            def tags = libOtel.getTags(appliance)
            def labels_separator = libOtel.getLabels("-l ${labels}")
            def intersection = libOtel.getIntersection(labels_separator, tags)
            commonLabels = intersection
            generation = libOtel.getGeneration(appliance)
            // println(intersection)
            // println(libOtel.getGeneration(appliance))
        }

        sh(script: """
        docker run --rm -e OTEL_EXPORTER_OTLP_ENDPOINT \
            dell/opentelemetry-cli:0.4.0 \
            metric counter tridevlab.test-counter \
            -a "test.labels=${commonLabels}" \
            -a "test.generation=${generation}"
    """, label: "Report OTel", returnStatus: true)
    }
    
}
