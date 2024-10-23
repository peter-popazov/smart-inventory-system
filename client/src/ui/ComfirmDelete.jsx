import PropTypes from "prop-types";
import Button from "./Button";

function ConfirmDelete({ resource, onConfirm, disabled, onCloseModal }) {
  function handleConfirmClick() {
    onConfirm?.();
    onCloseModal?.();
  }

  return (
    <div className="flex w-[40rem] flex-col gap-4">
      <h3 className="text-lg font-semibold md:text-xl">Delete {resource}</h3>
      <p className="mb-4 text-gray-500">
        Are you sure you want to delete {resource} permanently? This action
        cannot be undone.
      </p>

      <div className="flex justify-end gap-4">
        <Button
          textColor="text-gray-800"
          rounded="rounded-xl"
          className="border-2 border-violet-500 hover:bg-violet-100"
          onClick={onCloseModal}
        >
          Cancel
        </Button>
        <Button
          bgColor="bg-red-500"
          textColor="text-white"
          rounded="rounded-xl"
          className="hover:bg-red-700"
          onClick={handleConfirmClick}
          disabled={disabled}
        >
          Delete
        </Button>
      </div>
    </div>
  );
}

ConfirmDelete.propTypes = {
  resource: PropTypes.string.isRequired,
  onConfirm: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  onCloseModal: PropTypes.func.isRequired,
};

export default ConfirmDelete;
