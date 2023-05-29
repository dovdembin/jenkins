@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         

        def labels = "MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG"
        def arg1 = libOtel.getLabels("-l ${labels}")
        println(arg1)
    }
    
}
