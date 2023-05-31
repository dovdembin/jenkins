@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         
        def labels = "-l MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        def appliance = "WK-D0089"

        // def labels = "-l EX\\|MLK\\|Riptide,CopperBlade\\|LightBlade"
        // def appliance = "RT-D0201-RT-D0197-federation-TAG"
        
        tagsList = libOtel.getListTags(labels, appliance)
        generations = libOtel.getListGenertions(appliance)
        sh(script: """
        docker run --rm -e OTEL_EXPORTER_OTLP_ENDPOINT \
            dell/opentelemetry-cli:0.4.0 \
            metric counter tridevlab.test-counter \
            -a "str[]:test.my-array=${tagsList}" \
            -a "str[]:test.generation=${generations}"
    """, label: "Report OTel", returnStatus: true)
    }
    
}
