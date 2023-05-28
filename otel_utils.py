import sys

def method1(arg1, arg2):
    # Implement the logic for method1
    print(f"Executing method1 with arguments: {arg1}, {arg2}")

def method2(arg1):
    # Implement the logic for method2
    print(f"Executing method2 with argument: {arg1}")

def method3():
    # Implement the logic for method3
    print("Executing method3")

# Get the method name and arguments from command-line arguments
method_name = sys.argv[1]
args = sys.argv[2:]

# Call the appropriate method based on the provided method name
if method_name == "method1":
    method1(*args)
elif method_name == "method2":
    method2(*args)
elif method_name == "method3":
    method3()
else:
    print(f"Unknown method: {method_name}")