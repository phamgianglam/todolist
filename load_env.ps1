# Define the path to your .env file
$envFilePath = ".\.env"

# Check if the .env file exists
if (Test-Path $envFilePath) {
    # Read each line of the .env file
    Get-Content $envFilePath | ForEach-Object {
        # Trim whitespace from the line
        $line = $_.Trim()

        # Skip empty lines and comments
        if (-not [string]::IsNullOrWhiteSpace($line) -and -not $line.StartsWith("#")) {
            # Split the line into key and value
            $parts = $line -split "=", 2
            if ($parts.Count -eq 2) {
                $key = $parts[0].Trim()
                $value = $parts[1].Trim()

                # Set the environment variable
                [System.Environment]::SetEnvironmentVariable($key, $value)
            }
        }
    }
} else {
    Write-Error "The .env file was not found at path: $envFilePath"
}
